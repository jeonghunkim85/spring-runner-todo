package todoapp.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import todoapp.web.model.FeatureTogglesProperties;

@RestController
public class FeatureTogglesRestcontroller {

    private FeatureTogglesProperties featureTogglesProperties;

    public FeatureTogglesRestcontroller(FeatureTogglesProperties featureTogglesProperties) {
        this.featureTogglesProperties = featureTogglesProperties;
    }

    @GetMapping("/api/feature-toggles")
    public FeatureTogglesProperties featureTogglesProperties() {
        return featureTogglesProperties;
    }
}
