/*******************************************************************************
 * Copyright (c) 2019 Red Hat Inc. and others. All rights reserved. This program
 * and the accompanying materials which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors: Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package com.redhat.qute.settings.capabilities;

import static com.redhat.qute.settings.capabilities.ServerCapabilitiesConstants.COMPLETION_ID;
import static com.redhat.qute.settings.capabilities.ServerCapabilitiesConstants.DEFAULT_COMPLETION_OPTIONS;
import static com.redhat.qute.settings.capabilities.ServerCapabilitiesConstants.DOCUMENT_DEFINITION_ID;
import static com.redhat.qute.settings.capabilities.ServerCapabilitiesConstants.DOCUMENT_HIGHLIGHT_ID;
import static com.redhat.qute.settings.capabilities.ServerCapabilitiesConstants.DOCUMENT_SYMBOL_ID;
import static com.redhat.qute.settings.capabilities.ServerCapabilitiesConstants.TEXT_DOCUMENT_COMPLETION;
import static com.redhat.qute.settings.capabilities.ServerCapabilitiesConstants.TEXT_DOCUMENT_DEFINITION;
import static com.redhat.qute.settings.capabilities.ServerCapabilitiesConstants.TEXT_DOCUMENT_DOCUMENT_SYMBOL;
import static com.redhat.qute.settings.capabilities.ServerCapabilitiesConstants.TEXT_DOCUMENT_HIGHLIGHT;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.lsp4j.ClientCapabilities;
import org.eclipse.lsp4j.Registration;
import org.eclipse.lsp4j.RegistrationParams;
import org.eclipse.lsp4j.services.LanguageClient;

import com.redhat.qute.ls.commons.client.ExtendedClientCapabilities;

/**
 * Manages dynamic capabilities
 */
public class QuteCapabilityManager {

	private final Set<String> registeredCapabilities = new HashSet<>(3);
	private final LanguageClient languageClient;

	private ClientCapabilitiesWrapper clientWrapper;

	public QuteCapabilityManager(LanguageClient languageClient) {
		this.languageClient = languageClient;
	}

	/**
	 * Registers all dynamic capabilities that the server does not support client
	 * side preferences turning on/off
	 */
	public void initializeCapabilities() {
		if (this.getClientCapabilities().isDocumentHighlightDynamicRegistered()) {
			registerCapability(DOCUMENT_HIGHLIGHT_ID, TEXT_DOCUMENT_HIGHLIGHT);
		}
		if (this.getClientCapabilities().isDefinitionDynamicRegistered()) {
			registerCapability(DOCUMENT_DEFINITION_ID, TEXT_DOCUMENT_DEFINITION);
		}
		if (this.getClientCapabilities().isDocumentSymbolDynamicRegistrationSupported()) {
			registerCapability(DOCUMENT_SYMBOL_ID, TEXT_DOCUMENT_DOCUMENT_SYMBOL);
		}
		if (this.getClientCapabilities().isCompletionDynamicRegistrationSupported()) {
			registerCapability(COMPLETION_ID, TEXT_DOCUMENT_COMPLETION, DEFAULT_COMPLETION_OPTIONS);
		}
		/*
		 * if (this.getClientCapabilities().isCodeActionDynamicRegistered()) {
		 * registerCapability(CODE_ACTION_ID, TEXT_DOCUMENT_CODE_ACTION); } if
		 * (this.getClientCapabilities().isCodeLensDynamicRegistered()) {
		 * registerCapability(CODE_LENS_ID, TEXT_DOCUMENT_CODE_LENS); } if
		 * (this.getClientCapabilities().isCompletionDynamicRegistrationSupported()) {
		 * registerCapability(COMPLETION_ID, TEXT_DOCUMENT_COMPLETION,
		 * DEFAULT_COMPLETION_OPTIONS); } if
		 * (this.getClientCapabilities().isHoverDynamicRegistered()) {
		 * registerCapability(HOVER_ID, TEXT_DOCUMENT_HOVER); } if
		 * (this.getClientCapabilities().isDocumentSymbolDynamicRegistrationSupported())
		 * { registerCapability(DOCUMENT_SYMBOL_ID, TEXT_DOCUMENT_DOCUMENT_SYMBOL); } if
		 * (this.getClientCapabilities().isDefinitionDynamicRegistered()) {
		 * registerCapability(DEFINITION_ID, TEXT_DOCUMENT_DEFINITION); } if
		 * (this.getClientCapabilities().isFormattingDynamicRegistered()) {
		 * registerCapability(FORMATTING_ID, TEXT_DOCUMENT_FORMATTING); } if
		 * (this.getClientCapabilities().isFormattingDynamicRegistered()) {
		 * registerCapability(RANGE_FORMATTING_ID, TEXT_DOCUMENT_RANGE_FORMATTING); }
		 */
	}

	public void setClientCapabilities(ClientCapabilities clientCapabilities,
			ExtendedClientCapabilities extendedClientCapabilities) {
		this.clientWrapper = new ClientCapabilitiesWrapper(clientCapabilities, extendedClientCapabilities);
	}

	public ClientCapabilitiesWrapper getClientCapabilities() {
		if (this.clientWrapper == null) {
			this.clientWrapper = new ClientCapabilitiesWrapper();
		}
		return this.clientWrapper;
	}

	public Set<String> getRegisteredCapabilities() {
		return registeredCapabilities;
	}

	private void registerCapability(String id, String method) {
		registerCapability(id, method, null);
	}

	private void registerCapability(String id, String method, Object options) {
		if (registeredCapabilities.add(id)) {
			Registration registration = new Registration(id, method, options);
			RegistrationParams registrationParams = new RegistrationParams(Collections.singletonList(registration));
			languageClient.registerCapability(registrationParams);
		}
	}

}