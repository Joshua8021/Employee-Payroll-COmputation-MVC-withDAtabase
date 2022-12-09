package CS002FINAL;


public class MVCPayrollSystem {

    public static void main(String[] args) {
        PayrollView view = new PayrollView();
        PayrollModel model = new PayrollModel();
        PayrollController controller = new PayrollController(view, model);
        view.login();
    }

}
