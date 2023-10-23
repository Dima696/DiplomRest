package ru.netodology.backendnet.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "netolodip.files")
public class UserFile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "filename")
    private String filename;
    @OneToOne
    @JoinColumn(name = "user.id", referencedColumnName = "id")
    private User user;
    @Column(length = 1257)
    private String content;
    private LocalDateTime lastModifyingDateTime;
    private Long size;
}
