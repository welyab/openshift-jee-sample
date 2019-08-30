package com.welyab.telegram.teucubot;

import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

@Path("")
public class TelegramWebhook {

	@GET
	@Produces("text/plain")
	public Response test() {
		return Response.ok("Ok!").build();
	}

	@POST
	@Consumes("application/json")
	public Response onUpdate(Map<Object, Object> json) {
		if (!json.containsKey("message")) {
			return Response.ok().build();
		}

		Map<?, ?> message = (Map<?, ?>) json.get("message");
		Map<?, ?> from = (Map<?, ?>) message.get("from");
		if (!from.get("username").toString().equalsIgnoreCase("Aidentubot")) {
			return Response.ok().build();
		}

		Long messageId = Long.parseLong(message.get("message_id").toString());

		Map<?, ?> chat = (Map<?, ?>) message.get("chat");
		Long chatId = Long.parseLong(chat.get("id").toString());

		String parameters = new StringBuilder()
		.append("reply_to_message_id").append(messageId)
		.append("chat_id").append(chatId)
		.append("text").append("Teu CU!!!")
		.toString();

		ClientBuilder.newClient()
			.target("https://api.telegram.org/bot" + System.getenv("telegramtoken") + "/sendMessage?" + parameters)
			.request()
			.get()
			.close();

		return Response.ok().build();
	}
}
