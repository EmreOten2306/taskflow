package tech.ekya.taskflow.label;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import tech.ekya.taskflow.label.dto.CreateLabelRequest;
import tech.ekya.taskflow.label.dto.LabelResponse;
import tech.ekya.taskflow.label.dto.UpdateLabelRequest;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(
        name = "Labels",
        description = "Label management operations"
)
public class LabelController {

    private final LabelService labelService;

    public LabelController(LabelService labelService) {
        this.labelService = labelService;
    }

    @Operation(
            summary = "Create a label",
            description = "Creates a new label"
    )
    @ApiResponse(responseCode = "200", description = "Label created successfully")
    @ApiResponse(responseCode = "409", description = "Label already exists")
    @PostMapping("/labels")
    public LabelResponse createLabel(@Valid @RequestBody CreateLabelRequest request) {
        return labelService.createLabel(request);
    }

    @Operation(
            summary = "Get all labels",
            description = "Returns all labels"
    )
    @ApiResponse(responseCode = "200", description = "Labels retrieved successfully")
    @GetMapping("/labels")
    public List<LabelResponse> findAllLabels() {
        return labelService.findAllLabels();
    }

    @Operation(
            summary = "Get label by ID",
            description = "Returns a label by its ID"
    )
    @ApiResponse(responseCode = "200", description = "Label retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Label not found")
    @GetMapping("/labels/{id}")
    public LabelResponse findLabelById(@PathVariable Long id) {
        return labelService.findLabelById(id);
    }

    @Operation(
            summary = "Update a label",
            description = "Updates an existing label by its ID"
    )
    @ApiResponse(responseCode = "200", description = "Label updated successfully")
    @ApiResponse(responseCode = "404", description = "Label not found")
    @PutMapping("/labels/{id}")
    public LabelResponse updateLabel(
            @PathVariable Long id,
            @Valid @RequestBody UpdateLabelRequest request
    ) {
        return labelService.updateLabel(id, request);
    }

    @Operation(
            summary = "Delete a label",
            description = "Deletes a label by its ID"
    )
    @ApiResponse(responseCode = "200", description = "Label deleted successfully")
    @ApiResponse(responseCode = "404", description = "Label not found")
    @DeleteMapping("/labels/{id}")
    public void deleteLabelById(@PathVariable Long id) {
        labelService.deleteLabelById(id);
    }
}
