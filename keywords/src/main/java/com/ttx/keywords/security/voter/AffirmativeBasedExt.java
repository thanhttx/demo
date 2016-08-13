package com.ttx.keywords.security.voter;

import java.util.Collection;
import java.util.List;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.WebExpressionVoter;

public class AffirmativeBasedExt extends AffirmativeBased {

	public AffirmativeBasedExt(
			List<AccessDecisionVoter<? extends Object>> decisionVoters) {
		super(decisionVoters);
	}

	@Override
	public void decide(Authentication authentication, Object object,
			Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException {
		int deny = 0;

		for (AccessDecisionVoter voter : getDecisionVoters()) {

			if (voter instanceof WebExpressionVoter
					&& !(object instanceof FilterInvocation)) {
				continue;
			}
			if (voter instanceof PreInvocationAuthorizationAdviceVoterExt
					&& !(object instanceof MethodInvocation)) {
				continue;
			}

			int result = voter.vote(authentication, object, configAttributes);

			if (logger.isDebugEnabled()) {
				logger.debug("Voter: " + voter + ", returned: " + result);
			}

			switch (result) {
			case AccessDecisionVoter.ACCESS_GRANTED:
				return;

			case AccessDecisionVoter.ACCESS_DENIED:
				deny++;

				break;

			default:
				break;
			}
		}

		if (deny > 0) {
			throw new AccessDeniedException(messages.getMessage(
					"AbstractAccessDecisionManager.accessDenied",
					"Access is denied"));
		}

		// To get this far, every AccessDecisionVoter abstained
		checkAllowIfAllAbstainDecisions();
	}
}
