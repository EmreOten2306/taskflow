package tech.ekya.taskflow.label;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import tech.ekya.taskflow.label.dto.CreateLabelRequest;
import tech.ekya.taskflow.label.dto.LabelResponse;
import tech.ekya.taskflow.label.dto.UpdateLabelRequest;

import java.util.List;


@RestController
@RequestMapping("/api")

public class LabelController {
    private final LabelService labelService;
    public LabelController(LabelService labelService){
        this.labelService = labelService;
    }

    @PostMapping("/labels")
    public LabelResponse createLabel(@Valid @RequestBody CreateLabelRequest request) {

        return labelService.createLabel(request);
    }

    @GetMapping("/labels")
        public List<LabelResponse> findAllLabels() {
        return labelService.findAllLabels();
}
    @GetMapping("/labels/{id}")
    public LabelResponse findLabelById(@PathVariable Long id) {
        return labelService.findLabelById(id);
    }


    @PutMapping("/labels/{id}")
    public LabelResponse updateLabel(
            @PathVariable Long id,
            @Valid @RequestBody UpdateLabelRequest request
    ) {
        return labelService.updateLabel(id, request);
    }


    @DeleteMapping("/labels/{id}")
    public void deleteLabelById(@PathVariable Long id){
        labelService.deleteLabelById(id);
    }
}
