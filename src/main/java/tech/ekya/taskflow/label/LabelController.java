package tech.ekya.taskflow.label;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/tasks/{id}/labels/{labelId}")

public class LabelController {
    private final LabelService labelService;
    public LabelController(LabelService labelService){
        this.labelService = labelService;
    }




    @DeleteMapping
    public void deleteLabelById(Long id){
        labelService.deleteLabelById(id);
    }
}
