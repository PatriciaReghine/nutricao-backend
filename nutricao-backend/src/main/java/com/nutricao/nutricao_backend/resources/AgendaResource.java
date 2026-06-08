    package com.nutricao.nutricao_backend.resources;
    
    import com.nutricao.nutricao_backend.dto.agenda.AgendaCreateDTO;
    import com.nutricao.nutricao_backend.dto.agenda.AgendaDiaResponseDTO;
    import com.nutricao.nutricao_backend.dto.agenda.AgendaListResponseDTO;
    import com.nutricao.nutricao_backend.dto.agenda.AgendaStatusDTO;
    import com.nutricao.nutricao_backend.dto.nutricionista.NutricionistaAgendaDTO;
    import com.nutricao.nutricao_backend.entidades.Agenda;
    import com.nutricao.nutricao_backend.enums.StatusAgenda;
    import com.nutricao.nutricao_backend.repositories.AgendaRepositorie;
    import com.nutricao.nutricao_backend.services.AgendaService;
    import jakarta.validation.Valid;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.access.prepost.PreAuthorize;
    import org.springframework.web.bind.annotation.*;
    
    import java.util.List;
    
    @RestController
    @RequestMapping("/agendas")
    public class AgendaResource {
    
        @Autowired
        private AgendaService agendaService;

        @Autowired
        private AgendaRepositorie agendaRepositorie;



        // 📌 LISTAR AGENDAS (ABERTA / FINALIZADA)

        @PreAuthorize(
                "hasAnyRole('ADMINISTRADOR','SECRETARIA','NUTRICIONISTA')"
        )
        @GetMapping
        // ===============================
// 📋 LISTAR AGENDAS POR STATUS
// ===============================
        public List<AgendaListResponseDTO> listarAgendas(
                StatusAgenda status,
                Long nutricionistaId,
                Integer mes,
                Integer ano
        ) {

            List<Agenda> agendas =
                    agendaRepositorie.buscarAgendas(
                            status,
                            mes,
                            ano,
                            nutricionistaId
                    );

            return agendas.stream().map(agenda -> {

                AgendaListResponseDTO dto =
                        new AgendaListResponseDTO();

                dto.setId(
                        agenda.getId()
                );

                dto.setData(
                        agenda.getData().toString()
                );

                dto.setStatus(
                        agenda.getStatus()
                );

                NutricionistaAgendaDTO nutricionistaDTO =
                        new NutricionistaAgendaDTO();

                nutricionistaDTO.setId(
                        agenda.getUsuario().getId()
                );

                nutricionistaDTO.setNome(
                        agenda.getUsuario().getNome()
                );

                nutricionistaDTO.setCrn(
                        agenda.getUsuario().getCrn()
                );

                dto.setNutricionista(
                        nutricionistaDTO
                );

                return dto;

            }).toList();
        }


        @GetMapping("/dia")
        public ResponseEntity<AgendaDiaResponseDTO>
        buscarAgendaDoDia(
    
                @RequestParam String data,
                @RequestParam Long nutricionistaId
        ) {
    
            return ResponseEntity.ok(
                    agendaService.buscarAgendaDoDia(
                            data,
                            nutricionistaId
                    )
            );
        }
    
        // 📌 CRIAR AGENDA (opcional)

        @PreAuthorize(
                "hasAnyRole('ADMINISTRADOR','SECRETARIA','NUTRICIONISTA')"
        )
        @PostMapping
        public ResponseEntity<?> criarAgenda(@RequestBody @Valid AgendaCreateDTO dto) {
    
            agendaService.criarAgenda(dto);
    
            return ResponseEntity.ok("Agenda criada com sucesso");
        }
    
        // 📌 ALTERAR STATUS DA AGENDA (dia inteiro)
        @PutMapping("/{id}/status")
        public ResponseEntity<?> atualizarStatusAgenda(
                @PathVariable Long id,
                @Valid @RequestBody AgendaStatusDTO dto) {
    
            agendaService.atualizarStatusAgenda(id, dto.getStatus());
    
            return ResponseEntity.ok("Status da agenda atualizado");
        }
    }