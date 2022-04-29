package com.wowtown.wowtownbackend.common.domain;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class Interest {
    @Enumerated(EnumType.STRING)
    private InterestType type;

    protected Interest(){}

    public Interest(InterestType type){
        this.type= type;
    }
}
