package tech.ekya.taskflow.label;

import org.springframework.stereotype.Component;
import tech.ekya.taskflow.label.dto.CreateLabelRequest;
import tech.ekya.taskflow.label.dto.LabelResponse;
import tech.ekya.taskflow.label.dto.UpdateLabelRequest;
@Component
public class LabelMapper {

    public Label toEntity(CreateLabelRequest request) {
        Label label = new Label();
        label.setName(request.name());
        return label;
    }
    public void updateEntity(UpdateLabelRequest request, Label label) {
        label.setName(request.name());
    }

    public LabelResponse toResponse(Label label) {
        return new LabelResponse(
                label.getId(),
                label.getName()
        );
    }
}
