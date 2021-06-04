package com.gvendas.gestaovendas.excecao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

// Com esta anoatação, esta classe ficara responsavel 
// por ouvir e verificar se vai ocorrer alguma exceção
@ControllerAdvice
public class GestaoVendasExceptionHandler extends ResponseEntityExceptionHandler {

	private static final String CONSTANT_VALIDATION_LENGTH = "Length";
	private static final String CONSTANT_VALIDATION_NOT_BLANK = "NotBlank";

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<Error> errors = gerarListaDeErros(ex.getBindingResult());

		return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
	}

	private List<Error> gerarListaDeErros(BindingResult bindingResult) {

		List<Error> errors = new ArrayList<>();

		bindingResult.getFieldErrors().forEach(fieldError -> {
			String msgUsuario = tratarMensagemDeErroUsuario(fieldError);
			String msgDesenvolvedor = fieldError.toString();
			errors.add(new Error(msgUsuario, msgDesenvolvedor));
		});

		return errors;
	}

	private String tratarMensagemDeErroUsuario(FieldError fieldError) {

		if (fieldError.getCode().equals(CONSTANT_VALIDATION_NOT_BLANK)) {

			return fieldError.getDefaultMessage().concat(" é obrigatório!");
		}

		if (fieldError.getCode().equals(CONSTANT_VALIDATION_LENGTH)) {
			return fieldError.getDefaultMessage().concat(String.format(" deve ter entre %s e %s caracteres",
					fieldError.getArguments()[2], fieldError.getArguments()[1]));
		}
		return fieldError.toString();
	}

}
