package ru.contextguide.yandexservices;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.contextguide.adgroup.AdGroupAddItem;
import ru.contextguide.campaign.campaign.CampaignAddItem;
import ru.contextguide.campaign.campaign.CampaignGetItem;
import ru.contextguide.campaign.campaign.TextCampaignAddItem;
import ru.contextguide.campaign.textCampaign.*;
import ru.contextguide.yandexservices.adgroups.AdGroups;
import ru.contextguide.yandexservices.adgroups.AdGroupsDefaultImpl;
import ru.contextguide.yandexservices.campaigns.*;
import ru.contextguide.yandexservices.campaigns.DeleteResponse;
import ru.contextguide.yandexservices.exceptions.DeserializationException;
import ru.contextguide.yandexservices.exceptions.SerializationException;
import ru.contextguide.yandexservices.utils.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class MockObjects {
    private static final Logger log = LoggerFactory.getLogger(MockObjects.class);
    private static final JsonParser jsonParser = new DefaultJsonParser();
    private static final ServiceConnectionManager sce = new ServiceConnectionManagerDefaultImpl();
    private static final Campaigns campaigns = new CampaignsDefaultImpl(jsonParser, sce);
    private static final AdGroups adGroups = new AdGroupsDefaultImpl(jsonParser, sce);

    static {
        log.debug("MockObjects initialization started.");
        log.debug("JsonParser: " + jsonParser);
        log.debug("ServiceConnectionManager: " + sce);
        log.debug("Campaigns: " + campaigns);
        log.debug("AdGroups: " + adGroups);
        log.debug("MockObjects initializated.");
    }

    private MockObjects() {
    }

    public static Long createCampaignAddItem() throws Exception {
        log.debug("Creating campaign add item");
        CampaignAddItem mockCampaign = createDefaultCampaignAddItem();
        log.debug("Created mockCampaign: " + mockCampaign);
        CampaignsAddRequest addRequest = new CampaignsAddRequest(mockCampaign);
        log.debug("Created addRequest: " + addRequest);
        CampaignsAddResponse addResponse = campaigns.add(addRequest);
        log.debug("Received addResponse: " + addRequest);
        CampaignGetItem campaignGetItem = addResponse.getAddResults().get(0);
        log.debug("CampaignGetItem received: " + campaignGetItem);
        log.debug("CampaignGetItem id is " + campaignGetItem.getId());
        return campaignGetItem.getId();
    }

    public static void deleteCampaign(Long mockCampaignId) throws DeserializationException, IOException, SerializationException {
        log.debug("Deleteing campaign: " + mockCampaignId);
        IdsCriteria idsCriteria = new IdsCriteria(mockCampaignId);
        log.debug("IdsCriteria: " + idsCriteria);
        DeleteRequest deleteRequest = new DeleteRequest(idsCriteria);
        log.debug("DeleteRequest: " + deleteRequest);
        DeleteResponse deleteResponse = campaigns.delete(deleteRequest);
        assertNotNull(deleteResponse);
        log.debug("DeleteResponse received: " + deleteResponse);
        assertThat("1 campaign should be deleted", deleteResponse.getDeleteResults(), hasSize(1));
        log.debug(mockCampaignId + " campaign deleted.");
    }

    private static CampaignAddItem createDefaultCampaignAddItem() {
        log.debug("Creating campaignAddItem");
        DateTime tomorrowDate = DateTime.now().plusDays(1);
        DateTimeFormatter dtf = DateTimeFormat.forPattern("YYYY-MM-dd");
        String tomorrowString = dtf.print(tomorrowDate);
        log.info("Tomorrow date: " + tomorrowString);
        TextCampaignSearchStrategyAdd searchStrategy = new TextCampaignSearchStrategyAdd(TextCampaignSearchStrategyTypeEnum.HIGHEST_POSITION);
        log.debug("Search strategy: " + searchStrategy);
        TextCampaignNetworkStrategyAdd networkStrategy = new TextCampaignNetworkStrategyAdd(TextCampaignNetworkStrategyTypeEnum.MAXIMUM_COVERAGE);
        log.debug("Network strategyd: " + networkStrategy);
        TextCampaignStrategyAdd textCampaignStrategy = new TextCampaignStrategyAdd(searchStrategy, networkStrategy);
        log.debug("TextCampaignStrategyAdd: " + textCampaignStrategy);
        TextCampaignAddItem textCampaignItem = new TextCampaignAddItem(textCampaignStrategy);
        log.debug("TextCampaignAddItem: " + textCampaignItem);
        CampaignAddItem campaignAddItem = new CampaignAddItem("SomeCampaign", tomorrowString, textCampaignItem, null, null);
        log.debug("Created campaignAddItem: " + campaignAddItem);
        return campaignAddItem;
    }

    private AdGroupAddItem adgroupAddItem(Long campaignId) {
        //TODO logging?
        List<Long> regionIds = new ArrayList<>(1);
        regionIds.add(0L); // All regions
        return new AdGroupAddItem("SomeAdGroup", campaignId, regionIds);
    }
}
