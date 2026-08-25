package tech.ekya.taskflow.label;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")

public class LabelController {
    private final LabelService labelService;
    public LabelController(LabelService labelService){
        this.labelService = labelService;
    }

@PostMapping("/labels")
public Label createLabel(@RequestBody Label label) {
        return labelService.createLabel(label);
}

@GetMapping("/labels")
public List<Label> findAllLabels() {
        return labelService.findAllLabels();
}

@GetMapping ("/labels/{id}")
public Label findLabelById(@PathVariable Long id) {
        return labelService.findLabelById(id);
}

@PutMapping("/labels/{id}")
public Label updateLabel(@PathVariable Long id,
                         @RequestBody Label label) {
        return labelService.updateLabel(id,label);
}


    @DeleteMapping("/labels/{id}")
    public void deleteLabelById(@PathVariable Long id){
        labelService.deleteLabelById(id);
    }
}
