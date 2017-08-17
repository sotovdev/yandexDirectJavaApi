package ru.contextguide.yandexservices;

import ru.contextguide.yandexservices.adgroups.AdGroups;
import ru.contextguide.yandexservices.adgroups.AdGroupsImpl;
import ru.contextguide.yandexservices.ads.Ads;
import ru.contextguide.yandexservices.ads.AdsDefaultImpl;
import ru.contextguide.yandexservices.campaigns.Campaigns;
import ru.contextguide.yandexservices.campaigns.CampaignsDefaultImpl;
import ru.contextguide.yandexservices.changes.Changes;
import ru.contextguide.yandexservices.changes.ChangesDefaultImpl;
import ru.contextguide.yandexservices.utils.DefaultJsonParser;
import ru.contextguide.yandexservices.utils.JsonParser;
import ru.contextguide.yandexservices.utils.ServiceConnectionManager;
import ru.contextguide.yandexservices.utils.ServiceConnectionManagerDefaultImpl;

/**
 * Менеджер сервисов яндекса по умолчанию
 */
public class YandexServicesManagerDefaultImpl implements YandexServicesManager {
    private final JsonParser jsonParser = new DefaultJsonParser();
    private final ServiceConnectionManager serviceConnectionManager = new ServiceConnectionManagerDefaultImpl();

    @Override
    public AdGroups getAdGroupsService() {
        return new AdGroupsImpl(jsonParser, serviceConnectionManager);
    }

    @Override
    public Ads getAdsService() {
        return new AdsDefaultImpl(jsonParser, serviceConnectionManager);
    }

    @Override
    public Campaigns getCampaignsService() {
        return new CampaignsDefaultImpl(jsonParser, serviceConnectionManager);
    }

    @Override
    public Changes getChangesService() {
        return new ChangesDefaultImpl(jsonParser, serviceConnectionManager);
    }
}
