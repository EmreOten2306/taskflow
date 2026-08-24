package tech.ekya.taskflow.label;

import org.springframework.stereotype.Service;
import tech.ekya.taskflow.exception.ResourceNotFoundException;

@Service
public class LabelService {
    private final LabelRepository labelRepository;
    public LabelService(LabelRepository labelRepository){
        this.labelRepository = labelRepository;
    }

    public void deleteLabelById(Long id){
        Label label = labelRepository.findById(id)
                .orElseThrow(()  -> new ResourceNotFoundException(
                        "Label not found with id: " + id

                ));
            labelRepository.delete(label);
    }
}
