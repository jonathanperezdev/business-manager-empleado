package com.business.manager.services.implementations;

import com.business.manager.dao.entities.TipoDocumento;
import com.business.manager.dao.repositories.TipoDocumentoRepository;
import com.business.manager.exception.NoDataFoundException;
import com.business.manager.exception.error.ErrorEnum;
import com.business.manager.model.TipoDocumentoModel;
import com.business.manager.services.TipoDocumentoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TipoDocumentoServiceImpl implements TipoDocumentoService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TipoDocumentoRepository tipoDocumentoRepository;

    private Function<TipoDocumento, TipoDocumentoModel> toTipoDocumentoModel = tipoDocumento -> modelMapper.map(tipoDocumento , TipoDocumentoModel.class);

    @Override
    public List<TipoDocumentoModel> getAllTipoDocumentos() {
        List<TipoDocumentoModel> listTipoDocumentos = toModel(tipoDocumentoRepository.findAllByOrderByOrdenAsc());

        if(CollectionUtils.isEmpty(listTipoDocumentos)) {
            throw new NoDataFoundException(ErrorEnum.TIPO_DOCUMENTOS_NOT_FOUND);
        }
        return listTipoDocumentos;
    }

    private List<TipoDocumentoModel> toModel(List<TipoDocumento> entities) {
        return entities
                .stream()
                .map(toTipoDocumentoModel)
                .collect(Collectors.toList());
    }
}
