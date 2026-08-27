package tech.ekya.taskflow.label;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import tech.ekya.taskflow.exception.DuplicateResourceException;
import tech.ekya.taskflow.exception.ResourceNotFoundException;
import tech.ekya.taskflow.label.dto.CreateLabelRequest;
import tech.ekya.taskflow.label.dto.LabelResponse;
import tech.ekya.taskflow.label.dto.UpdateLabelRequest;

import java.util.List;
@Transactional
@Service
public class LabelService {
    private final LabelRepository labelRepository;
    private final LabelMapper labelMapper;
    public LabelService(LabelRepository labelRepository,
                        LabelMapper labelMapper) {
        this.labelRepository = labelRepository;
        this.labelMapper = labelMapper;
    }

            ///CREATE LABEL
    public LabelResponse createLabel(CreateLabelRequest request) {

        if (labelRepository.existsByName(request.name())) {
            throw new DuplicateResourceException(
                    "Label already exists: " + request.name()
            );
        }
        Label label = labelMapper.toEntity(request);
        Label savedLabel = labelRepository.save(label);
        return labelMapper.toResponse(savedLabel);
    }
            /// GET ALL LABELS
    public List<LabelResponse> findAllLabels() {

        return labelRepository.findAll()
                .stream()
                .map(labelMapper::toResponse)
                .toList();
    }

    public LabelResponse findLabelById(Long id) {

        Label label = labelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Label not found with id: " + id
                ));

        return labelMapper.toResponse(label);



    }
    public LabelResponse updateLabel(
            Long id,
            UpdateLabelRequest request
    ) {
        Label existingLabel = labelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Label not found with id: " + id
                ));

        labelMapper.updateEntity(request, existingLabel);

        Label savedLabel = labelRepository.save(existingLabel);

        return labelMapper.toResponse(savedLabel);
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
