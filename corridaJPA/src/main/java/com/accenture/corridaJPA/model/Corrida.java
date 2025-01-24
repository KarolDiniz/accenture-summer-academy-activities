package com.accenture.corridaJPA.model;

import java.io.Serializable;
import java.time.OffsetDateTime;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Corrida implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCorrida;

    @Column(nullable = false)
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
	private OffsetDateTime dataCorrida;

    @ManyToOne
    @JoinColumn(name = "idLocal", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Local local;
    
	public Long getIdCorrida() {
		return idCorrida;
	}

	public void setIdCorrida(Long idCorrida) {
		this.idCorrida = idCorrida;
	}

	public OffsetDateTime getDataCorrida() {
		return dataCorrida;
	}

	public void setDataCorrida(OffsetDateTime dataCorrida) {
		this.dataCorrida = dataCorrida;
	}

	public Local getLocal() {
		return local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}


}

