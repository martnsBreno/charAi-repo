package org.martns.controller;

import org.martns.dto.ExceptionDto;
import org.martns.dto.UserBackResponseDto;
import org.martns.dto.UserPromptDto;
import org.martns.exception.MuitasRequisicoesException;
import org.martns.exception.SemAutorizacaoException;
import org.martns.service.AiCallService;

import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/test")
@ApplicationScoped
public class AiController {

    @Inject
    AiCallService aiCallService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response consumeAi(UserPromptDto userPromptDto) throws JsonProcessingException {

        try {

            UserBackResponseDto responseUser = aiCallService.realizarChamada(userPromptDto);

            return Response.ok().entity(responseUser).build();

        } catch (SemAutorizacaoException e) {
            return Response.status(400)
                    .entity(new ExceptionDto(e.getMessage())).build();
        } catch (MuitasRequisicoesException e) {
            return Response.status(400)
                    .entity(new ExceptionDto(e.getMessage())).build();
        } 
    }

}
