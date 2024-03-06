package acme.entities.progress_logs;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ProgressLog extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @Pattern(regexp = "PG-[A-Z]{1,2}-[0-9]{4}", message = "Record id must match pattern PG-[A-Z]{1,2}-[0-9]{4}")
    @NotBlank
    private String recordId;

    @NotNull
    private double completenessPercentage;

    @NotBlank
    private String progressComment;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date registrationMoment;

    @NotBlank
    private String responsiblePerson;

}
