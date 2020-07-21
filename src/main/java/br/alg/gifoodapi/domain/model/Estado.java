package br.alg.gifoodapi.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

import br.alg.gifoodapi.core.validation.Groups;
import br.alg.gifoodapi.core.validation.Groups.EstadoId;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Estado {

	@NotNull(groups = Groups.EstadoId.class)
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	@NotBlank(groups = {Default.class, EstadoId.class})
	@Column(nullable = false)
	private String nome;
	
}
