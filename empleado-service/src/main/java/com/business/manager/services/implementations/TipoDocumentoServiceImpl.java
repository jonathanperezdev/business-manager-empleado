package com.business.manager.services.implementations;

import com.business.manager.dao.entities.Empleado;
import com.business.manager.dao.entities.TipoDocumento;
import com.business.manager.dao.repositories.TipoDocumentoRepository;
import com.business.manager.exception.NoDataFoundException;
import com.business.manager.exception.error.ErrorEnum;
import com.business.manager.model.EmpleadoModel;
import com.business.manager.model.TipoDocumentoModel;
import com.business.manager.services.TipoDocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TipoDocumentoServiceImpl implements TipoDocumentoService {

    @Autowired
    private TipoDocumentoRepository tipoDocumentoRepository;

    @Autowired
    @Qualifier("customConversionService")
    private ConversionService conversionService;

    @Override
    public List<TipoDocumentoModel> getAllTipoDocumentos() {
        List<TipoDocumentoModel> listTipoDocumentos = toModel(tipoDocumentoRepository.findAllByOrderByOrdenAsc());

        if(CollectionUtils.isEmpty(listTipoDocumentos)) {
            throw new NoDataFoundException(ErrorEnum.TIPO_DOCUMENTOS_NOT_FOUND);
        }
        return listTipoDocumentos;
    }

    private List<TipoDocumentoModel> toModel(List<TipoDocumento> listTipoDocumentos) {
        return listTipoDocumentos
                .stream()
                .map(tipoDocumento -> conversionService.convert(tipoDocumento, TipoDocumentoModel.class))
                .collect(Collectors.toList());
    }
}
