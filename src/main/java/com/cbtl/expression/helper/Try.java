package com.cbtl.expression.helper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.Collections;
import java.util.List;

@Value
@Builder
@AllArgsConstructor
public class Try {
    Boolean result;
    String reason;
    @Builder.Default
    List<Try> childTries = Collections.emptyList();
}
