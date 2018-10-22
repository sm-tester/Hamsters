package com.unrealmojo.hamsters.repos;

import com.unrealmojo.hamsters.models.Hamster;

import java.util.List;

public class HamstersResp {
    private List<Hamster> data;
    private RequestStatus status;

    public HamstersResp(List<Hamster> data) {
        this.data = data;
        this.status = RequestStatus.SUCCESS;
    }

    public HamstersResp() {
        this.status = RequestStatus.FAILED;
    }

    public List<Hamster> getData() {
        return data;
    }

    public boolean isRequestOK() {
        return status == RequestStatus.SUCCESS;
    }
}
