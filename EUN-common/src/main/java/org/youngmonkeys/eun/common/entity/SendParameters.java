package org.youngmonkeys.eun.common.entity;

import lombok.Data;

@Data
public class SendParameters {
    boolean encrypted;
    boolean unreliable;
}
