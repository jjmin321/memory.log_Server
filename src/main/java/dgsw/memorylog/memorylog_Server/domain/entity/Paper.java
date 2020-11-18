package dgsw.memorylog.memorylog_Server.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import dgsw.memorylog.memorylog_Server.enums.PaperScope;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "paper")
@Getter
@Setter
public class Paper {
    @Id
    @Column()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    @ManyToOne(targetEntity=Member.class, fetch=FetchType.EAGER)
    @JoinColumn(columnDefinition = "member_idx")
    private Member member;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private PaperScope scope;

    @Column()
    private String code;

    @Column()
    private String thumbnail;

    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @JsonProperty("end_time")
    private LocalDateTime endTime;

    @CreatedDate
    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    @JsonProperty("created_at")
    private Date createdAt = new Date();

    @UpdateTimestamp()
    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @JsonProperty("updated_at")
    private Date updatedAt;
}
