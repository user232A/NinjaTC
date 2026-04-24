package api.requests.skeleton.interfaces;

import api.models.BaseModel;

public interface CrudEndpointInterface {
    Object post(BaseModel model);
    Object get();
    Object put(BaseModel model);
    void put(String text);
    Object delete(int id);
}
