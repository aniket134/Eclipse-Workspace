package edu.iit.roorkee.placement.users.student.actions;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.iit.roorkee.common.Constants;
import edu.iit.roorkee.common.data.DataNotFoundException;
import edu.iit.roorkee.common.data.Transaction;
import edu.iit.roorkee.placement.users.company.data.CompanyDetails;
import edu.iit.roorkee.placement.users.student.data.Alumni;
import edu.iit.roorkee.placement.users.student.data.GotJob;
import edu.iit.roorkee.placement.users.student.data.PersonalInformation;

public class AddAlumniInfo extends Action {

	// Global Forwards
	public static final String GLOBAL_FORWARD_welcome = "welcome";
	public static final String GLOBAL_FORWARD_curricular = "education";
	private Log log = LogFactory.getLog(getClass().getName());

	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Vector gotJobs = new Vector();
		Vector crit = new Vector();
		Vector results = new Vector();
		GotJob gj = new GotJob();
		String enrollment_no = "";
		int company_id = 0;
		PersonalInformation p = new PersonalInformation();
		PersonalInformation p2 = new PersonalInformation();
		CompanyDetails cd = new CompanyDetails();
		CompanyDetails cd2 = new CompanyDetails();
		try {
			if(!Constants.isAdmin(((String)request.getSession().getAttribute("personid")))) {
				return mapping.findForward("unauthorised");
			}
			Transaction t = new Transaction();
			gotJobs = getGotJobs();
			log.info("gotjob size: " + gotJobs.size());
			for(int i = 0; i < gotJobs.size(); i++) {
				gj = (GotJob)gotJobs.get(i);
				Alumni alumni = new Alumni();
				enrollment_no = gj.getEnrollmentNo();
				company_id = gj.getCompanyId();
				t = new Transaction();
				p = new PersonalInformation();
				cd = new CompanyDetails();
				try {
					results.clear();
					crit.clear();
					crit.add(enrollment_no);
					t.begin();
					p.setDatabase(t.getDatabase());
					results = p.search("select pi from edu.iit.roorkee.placement.users.student.data.PersonalInformation pi where pi.enrollmentNo=$1",crit);
					t.commit();
					p2 = (PersonalInformation)results.elementAt(0);
					log.info("got person: " + p2.getPersonId());

					results.clear();
					crit.clear();
					crit.add(company_id);
					t.begin();
					cd.setDatabase(t.getDatabase());
					results = cd.search("select cd from edu.iit.roorkee.placement.users.company.data.CompanyDetails cd where cd.companyId=$1", crit);
					t.commit();
					cd2 = (CompanyDetails)results.elementAt(0);
					log.info("got companyDetails: " + cd2.getNameOfCompany());
					
					t = new Transaction();
					results.clear();
					crit.clear();
					t.begin();
					alumni.setDatabase(t.getDatabase());
					BeanUtils.copyProperties(alumni, p2);
					BeanUtils.copyProperties(alumni, cd2);
					alumni.setGraduationYear(Integer.toString(GregorianCalendar.getInstance().get(Calendar.YEAR)));
					alumni.create();
					t.commit();
					log.info("Successfully created Alumni Data.");
					request.setAttribute("msg", "here there");
				} catch (DataNotFoundException de) {
					t.rollback();
					request.setAttribute("msg", de.getMessage());
				} catch(Exception e) {
					t.rollback();
					log.info(e);
					e.printStackTrace();
					request.setAttribute("msg", e.getMessage());
				} finally {
					t.close();
				}
			}
		} catch(Exception e) {
			log.info(e);
		}
		return mapping.findForward("welcome");
	}

	@SuppressWarnings("unchecked")
	Vector getGotJobs() throws Exception {
		Vector results = new Vector();
		Transaction t = new Transaction();
		GotJob gj = new GotJob();
		t.begin();
		gj.setDatabase(t.getDatabase());
		results = gj.search("select gj from edu.iit.roorkee.placement.users.student.data.GotJob gj", new Vector());
		return results;
	}
}
