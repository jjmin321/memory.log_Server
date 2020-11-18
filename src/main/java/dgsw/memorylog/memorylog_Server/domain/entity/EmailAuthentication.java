package dgsw.memorylog.memorylog_Server.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "email_authentication")
@Getter
@Setter
public class EmailAuthentication {
    @Id
    @Column(name = "idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

   @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    @JsonProperty("expire_time")
    private Date expireTime;

    @Column(nullable = true)
    private String code;

    @Column(nullable = false)
    @JsonProperty("is_certified")
    private Boolean isCertified = false;
}
