package service;

import java.util.List;

import beans.IdGenerator;
import beans.TypeOfMembership;
import dto.MembershipDTO;
import repository.MembershipDTORepository;

public class MembershipDTOService {

	private MembershipDTORepository membershipDTORepository = new MembershipDTORepository();
	private IdGenerator idGenerator = new IdGenerator();
	
	public void add() {
		membershipDTORepository.addOne(new MembershipDTO(idGenerator.generateRandomKey(4), TypeOfMembership.Monthly, 4000, 30));
	}
	
	public List<MembershipDTO> getAll(){
		return membershipDTORepository.getAll();
	}
	
	public MembershipDTO findMembershipById(String membershipId) {
		for(MembershipDTO membershipDTO: membershipDTORepository.getAll()) {
			if(membershipDTO.getMembershipID().equals(membershipId)) {
				return membershipDTO;
			}
		}
		return null;
	}
	
}
