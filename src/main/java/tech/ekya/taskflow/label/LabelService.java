package tech.ekya.taskflow.label;

import org.springframework.stereotype.Service;
import tech.ekya.taskflow.exception.DuplicateResourceException;
import tech.ekya.taskflow.exception.ResourceNotFoundException;

import java.util.List;

@Service
public class LabelService {
    private final LabelRepository labelRepository;
    public LabelService(LabelRepository labelRepository){
        this.labelRepository = labelRepository;
    }

            ///CREATE LABEL
    public Label createLabel(Label label) {

        if (labelRepository.existsByName(label.getName())) {
            throw new DuplicateResourceException(
                    "Label already exists: " + label.getName()
            );
        }
        return labelRepository.save(label);
    }

            ///GET ALL LABELS
    public List<Label> findAllLabels() {
        return labelRepository.findAll();
    }

        ///GET LABEL FİNDBYID
    public Label findLabelById(Long id) {

        return labelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Label not found with id: " + id
                ));


    }
            ///UPDATE LABEL BY ID
    public Label updateLabel(Long id, Label label) {
        Label existingLabel = labelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Label not found with id: " + id
                        ));

        existingLabel.setName(label.getName());

        return labelRepository.save(existingLabel);
    }


            ///DELETE LABEL
    public void deleteLabelById(Long id){
        Label label = labelRepository.findById(id)
                .orElseThrow(()  -> new ResourceNotFoundException(
                        "Label not found with id: " + id

                ));
            labelRepository.delete(label);
    }
}
