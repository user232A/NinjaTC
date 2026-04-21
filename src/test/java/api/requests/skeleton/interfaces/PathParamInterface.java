package api.requests.skeleton.interfaces;

import api.requests.skeleton.requesters.CrudRequester;

public interface PathParamInterface {
    CrudRequester withPathParam(String param, Number value);
}
