package com.pushsignal.controllers;

import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.pushsignal.domain.EventInvite;
import com.pushsignal.domain.EventMember;
import com.pushsignal.logic.InviteLogic;
import com.pushsignal.xml.jaxb.DeleteResultDTO;
import com.pushsignal.xml.jaxb.EventInviteDTO;
import com.pushsignal.xml.jaxb.EventInviteSetDTO;
import com.pushsignal.xml.jaxb.EventMemberDTO;

@Scope("singleton")
@Controller
public class InviteController extends AbstractController {
	@Autowired
	private InviteLogic inviteLogic;

	/**
	 * Get a list of all open invites for the logged-in user.
	 * 
	 * @return XML model of a set of EventInvites.
	 */
	@RequestMapping(value="/invites/all")
	public ModelAndView getAllInvites(final HttpServletResponse response) {
		try {
			final Set<EventInvite> eventInvites = inviteLogic.getAllInvites(getAuthenticatedEmail());
			final EventInviteSetDTO eventInviteDTOs = new EventInviteSetDTO();
			for (EventInvite eventInvite : eventInvites) {
				eventInviteDTOs.getEventInvites().add(new EventInviteDTO(eventInvite));
			}
			return getXMLModelAndView("invites", eventInviteDTOs);
		} catch (final Exception ex) {
			return getErrorModelAndView(response, ex);
		}
	}

	/**
	 * Get details about a particular invite.
	 * 
	 * @param inviteId
	 * @return XML model of the invite.
	 */
	@RequestMapping(value="/invites/{inviteId}")
	public ModelAndView getInvite(final HttpServletResponse response,
			@PathVariable final long inviteId) {
		try {
			final EventInvite eventInvite = inviteLogic.getInvite(getAuthenticatedEmail(), inviteId);
			return getXMLModelAndView("invite", new EventInviteDTO(eventInvite));
		} catch (final Exception ex) {
			return getErrorModelAndView(response, ex);
		}
	}

	/**
	 * Invite another user to an event that you are a member of.
	 * 
	 * @param eventId
	 * @param email
	 * @return XML model of the EventInvite.
	 */
	@RequestMapping(value="/invites/create", method = RequestMethod.POST)
	public ModelAndView createInvite(final HttpServletResponse response,
			@RequestParam final long eventId,
			@RequestParam final String email) {
		try {
			final EventInvite eventInvite = inviteLogic.createInvite(getAuthenticatedEmail(), eventId, email);
			return getXMLModelAndView("invite", new EventInviteDTO(eventInvite));
		} catch (final Exception ex) {
			return getErrorModelAndView(response, ex);
		}
	}

	/**
	 * Accept an invite that was sent to you.
	 * 
	 * @param inviteId
	 * @return XML model of the EventMember.
	 */
	@RequestMapping(value="/invites/{inviteId}/accept", method = RequestMethod.POST)
	public ModelAndView acceptInvite(final HttpServletResponse response,
			@PathVariable final long inviteId) {
		try {
			final EventMember eventMember = inviteLogic.acceptInvite(getAuthenticatedEmail(), inviteId);
			return getXMLModelAndView("eventMember", new EventMemberDTO(eventMember));
		} catch (final Exception ex) {
			return getErrorModelAndView(response, ex);
		}
	}

	/**
	 * Decline an invite that was sent to you.
	 * 
	 * @param inviteId
	 * @return XML model of the DeleteResult.
	 */
	@RequestMapping(value="/invites/{inviteId}", method = RequestMethod.DELETE)
	public ModelAndView declineInvite(final HttpServletResponse response,
			@PathVariable final long inviteId) {
		try {
			inviteLogic.declineInvite(getAuthenticatedEmail(), inviteId);
			return getXMLModelAndView("eventInvite", new DeleteResultDTO("eventInvite"));
		} catch (final Exception ex) {
			return getErrorModelAndView(response, ex);
		}
	}
}
