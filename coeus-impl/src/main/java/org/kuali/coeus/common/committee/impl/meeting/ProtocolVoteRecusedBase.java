/*
 * Kuali Coeus, a comprehensive research administration system for higher education.
 * 
 * Copyright 2005-2016 Kuali, Inc.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.kuali.coeus.common.committee.impl.meeting;


/**
 * 
 * This class is for member recused from vote.
 */
public abstract class ProtocolVoteRecusedBase extends ProtocolMeetingVoterBase {

    private static final long serialVersionUID = 6207540592702779518L;

    private Long protocolVoteRecusedId;

    public ProtocolVoteRecusedBase() {
    }

    public Long getProtocolVoteRecusedId() {
        return protocolVoteRecusedId;
    }

    public void setProtocolVoteRecusedId(Long protocolVoteRecusedId) {
        this.protocolVoteRecusedId = protocolVoteRecusedId;
    }
}
