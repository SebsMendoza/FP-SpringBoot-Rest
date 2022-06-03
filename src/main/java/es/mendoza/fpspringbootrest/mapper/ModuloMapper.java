package es.mendoza.fpspringbootrest.mapper;

import es.mendoza.fpspringbootrest.dto.modulos.CreateModuloDTO;
import es.mendoza.fpspringbootrest.dto.modulos.ListModulosDTO;
import es.mendoza.fpspringbootrest.dto.modulos.ModuloDTO;
import es.mendoza.fpspringbootrest.models.Modulo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ModuloMapper {
    private final ModelMapper modelMapper;

    public ModuloDTO toDTO(Modulo modulo) {
        return modelMapper.map(modulo, ModuloDTO.class);
    }

    public Modulo fromDTO(ModuloDTO moduloDTO) {
        return modelMapper.map(moduloDTO, Modulo.class);
    }

    public List<ModuloDTO> toDTO(List<Modulo> modulos) {
        return modulos.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Modulo fromDTO(CreateModuloDTO moduloDTO) {
        return modelMapper.map(moduloDTO, Modulo.class);
    }

    public ListModulosDTO toListDTO(List<Modulo> modulos) {
        ListModulosDTO listModulosDTO = new ListModulosDTO();
        listModulosDTO.setData(modulos.stream().map(this::toDTO).collect(Collectors.toList()));
        return listModulosDTO;
    }
}
