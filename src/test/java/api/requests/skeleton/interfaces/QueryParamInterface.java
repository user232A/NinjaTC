package api.requests.skeleton.interfaces;

import api.requests.skeleton.requesters.CrudRequester;

public interface QueryParamInterface {
    CrudRequester withQueryParam(String param, String value);
}
