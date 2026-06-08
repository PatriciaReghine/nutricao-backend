package com.nutricao.nutricao_backend.entidades;

import com.nutricao.nutricao_backend.enums.StatusAgenda;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "agenda")
public class Agenda implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate data;

    @Enumerated(EnumType.STRING)
    private StatusAgenda status;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @OneToMany(mappedBy = "agenda")
    private List<ItensAgenda> itens;

    public Agenda() {
    }

    public Agenda(Long id, LocalDate data, StatusAgenda status) {
        this.id = id;
        this.data = data;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public StatusAgenda getStatus() {
        return status;
    }

    public void setStatus(StatusAgenda status) {
        this.status = status;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<ItensAgenda> getItens() {
        return itens;
    }

    public void setItens(List<ItensAgenda> itens) {
        this.itens = itens;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Agenda agenda = (Agenda) o;
        return Objects.equals(id, agenda.id) && Objects.equals(data, agenda.data) && status == agenda.status && Objects.equals(usuario, agenda.usuario) && Objects.equals(itens, agenda.itens);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, data, status, usuario, itens);
    }
}
