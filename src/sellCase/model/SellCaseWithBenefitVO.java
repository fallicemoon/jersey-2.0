package sellCase.model;

public class SellCaseWithBenefitVO extends SellCaseVO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6417685656982413917L;
	private Integer benefit;
	private Integer estimateBenefit;
	private Integer costs;
	private Integer agentCosts;
	
	public Integer getBenefit() {
		return benefit;
	}
	public void setBenefit(Integer benefit) {
		this.benefit = benefit;
	}
	public Integer getEstimateBenefit() {
		return estimateBenefit;
	}
	public void setEstimateBenefit(Integer estimateBenefit) {
		this.estimateBenefit = estimateBenefit;
	}
	public Integer getCosts() {
		return costs;
	}
	public void setCosts(Integer costs) {
		this.costs = costs;
	}
	public Integer getAgentCosts() {
		return agentCosts;
	}
	public void setAgentCosts(Integer agentCosts) {
		this.agentCosts = agentCosts;
	}
	
	@Override
	public String toString() {
		return "SellCaseWithBenefitVo [benefit=" + benefit + ", estimateBenefit=" + estimateBenefit + ", costs=" + costs
				+ ", agentCosts=" + agentCosts + "]";
	}
	
	

}
