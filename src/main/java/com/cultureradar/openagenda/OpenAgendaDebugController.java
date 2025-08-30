// src/main/java/com/cultureradar/openagenda/OpenAgendaDebugController.java
package com.cultureradar.openagenda;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/_debug")
public class OpenAgendaDebugController {

    private final OpenAgendaProperties props;

    public OpenAgendaDebugController(OpenAgendaProperties props) {
        this.props = props;
    }

    @GetMapping("/openagenda")
    public Map<String, Object> debug() {
        String maskedKey = props.publicKey() == null ? null :
                props.publicKey().length() <= 6 ? "******" :
                        props.publicKey().substring(0, 3) + "*****" + props.publicKey().substring(props.publicKey().length() - 3);
        return Map.of(
                "agendaUid", props.agendaUid(),
                "publicKeyPresent", props.publicKey() != null && !props.publicKey().isBlank(),
                "publicKeyMasked", maskedKey
        );
    }
}
