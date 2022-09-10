package service;

import java.util.List;

import beans.Customer;
import beans.IdGenerator;
import beans.TypeName;
import beans.TypeOfMembership;
import dto.MembershipDTO;
import repository.MembershipDTORepository;

public class MembershipDTOService {

	private MembershipDTORepository membershipDTORepository = new MembershipDTORepository();
	private IdGenerator idGenerator = new IdGenerator();
	
	public void add() {
		membershipDTORepository.addOne(new MembershipDTO(idGenerator.generateRandomKey(4), TypeOfMembership.Monthly, 4000, 30));
	}
	
	public List<MembershipDTO> getAll(Customer customer){
		List<MembershipDTO> memberships = membershipDTORepository.getAll();
		for(MembershipDTO membership: memberships) {
			if(customer.getType().getDiscount() == 0) {
				membership.setPrice(membership.getPrice() * 1);
			}else if(customer.getType().getDiscount() == 5) {
				membership.setPrice(membership.getPrice() * 0.95);
			}else if(customer.getType().getDiscount() == 10) {
				membership.setPrice(membership.getPrice() * 0.9);
			}
		}
		return memberships;
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
