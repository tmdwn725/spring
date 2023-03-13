package com.example.demo.club.domain;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "file")
public class File {
	
	@Id
	@Column(name ="file_seq")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long fileSeq;

	@Column(name = "file_Nm")
	private String fileNm;
	    
	@Column(name = "file_pth")
	private String filePth;
	    
	@Column(name = "file_ext")
	private String fileExt;

	@OneToOne(mappedBy = "file")
	private Club club;

	public void createFile(String fileNm, String filePth, String fileExt) {
		this.fileNm = fileNm;
		this.filePth = filePth;
		this.fileExt = fileExt;
	}
}
