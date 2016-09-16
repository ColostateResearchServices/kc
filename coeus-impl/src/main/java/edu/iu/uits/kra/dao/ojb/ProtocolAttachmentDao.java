package edu.iu.uits.kra.dao.ojb;

import java.util.ArrayList;

public interface ProtocolAttachmentDao {

	public byte[] getAttachmentData(Long fileId);
	
	public void saveAttachmentData(Long fileId, byte[] attachmentData);
	
	public ArrayList<Long> checkProtocolForNullFileData(Long protocolId);
	
	public ArrayList<Long> checkNegotiationForNullFileData(Long negotiationId);
	
}
