package carservicecrm.controllers;

import carservicecrm.models.*;
import carservicecrm.models.enums.Role;
import carservicecrm.services.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.security.Principal;
import java.time.LocalTime;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AdminControllerTest {

    private AdminController adminController;

    @Mock
    private UserService userService;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private WorkerService workerService;

    @Mock
    private ManufacturerService manufacturerService;

    @Mock
    private OperatorService operatorService;

    @Mock
    private StoService stoService;

    @Mock
    private OfferService offerService;

    @Mock
    private ReviewService reviewService;

    @Mock
    private DetailService detailService;

    @Mock
    private DetailProviderService detailProviderService;

    @Mock
    private AdministratorService administratorService;

    @Mock
    private PurchaseService purchaseService;

    @Mock
    private WorkerRequestService workerRequestService;

    @Mock
    private ToolService toolService;

    @Mock
    private AppearanceSettingsService appearanceSettingsService;


    @Mock
    private Model model;

    @Mock
    private Principal principal;

    private User user;
    private Employee employee;
    private MultiValueMap<String, String> form;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        adminController = new AdminController(userService, employeeService, workerService,
                manufacturerService, operatorService, stoService, offerService, reviewService,
                detailService, detailProviderService, administratorService, purchaseService,
                workerRequestService, toolService, appearanceSettingsService);
        user = new User();
        user.setId(1L);
        employee = new Employee();
        employee.setId(1L);
        form = new LinkedMultiValueMap<>();
        form.add("sto1", "value");
        form.add("sto2", "value");
    }

    @Test
    void testGetAdminPage() {
        when(principal.getName()).thenReturn("admin");
        User user = new User();
        when(userService.getUserByPrincipal(principal)).thenReturn(user);

        String viewName = adminController.admin(model, principal);

        assertEquals("admin", viewName);
        verify(model).addAttribute("user", user);
    }

    @Test
    void testGetAdminUsersPage() {
        User user = new User();
        when(userService.list()).thenReturn(Collections.singletonList(user));
        when(principal.getName()).thenReturn("admin");
        when(userService.getUserByPrincipal(principal)).thenReturn(user);

        String viewName = adminController.adminusers(model, principal);

        assertEquals("admin-users", viewName);
        verify(model).addAttribute("users", Collections.singletonList(user));
    }

    @Test
    void testGetAdminUsersPage_EmptyList() {
        when(userService.list()).thenReturn(Collections.emptyList());
        when(principal.getName()).thenReturn("admin");
        when(userService.getUserByPrincipal(principal)).thenReturn(new User());

        String viewName = adminController.adminusers(model, principal);

        assertEquals("admin-users", viewName);
        verify(model).addAttribute("users", Collections.emptyList());
    }

    @Test
    public void testAdminActiveUsers() {
        when(userService.activeUsers()).thenReturn(List.of(new User(), new User()));
        when(userService.getUserByPrincipal(principal)).thenReturn(new User());

        String viewName = adminController.adminactiveusers(model, principal);

        verify(model).addAttribute("users", userService.activeUsers());
        verify(model).addAttribute("user", userService.getUserByPrincipal(principal));
        assertThat(viewName).isEqualTo("admin-active-users");
    }

    @Test
    public void testAdminClients() {
        when(userService.listClients()).thenReturn(List.of(new User(), new User()));
        when(userService.getUserByPrincipal(principal)).thenReturn(new User());

        String viewName = adminController.adminclients(model, principal);

        verify(model).addAttribute("clients", userService.listClients());
        verify(model).addAttribute("user", userService.getUserByPrincipal(principal));
        assertThat(viewName).isEqualTo("admin-clients");
    }

    @Test
    public void testAdminWorkers() {
        when(workerService.list()).thenReturn(List.of(new Worker(), new Worker()));
        when(userService.getUserByPrincipal(principal)).thenReturn(new User());

        String viewName = adminController.adminworkers(model, principal);

        verify(model).addAttribute("workers", workerService.list());
        verify(model).addAttribute("user", userService.getUserByPrincipal(principal));
        assertThat(viewName).isEqualTo("admin-workers");
    }

    @Test
    public void testAdminManufacturers() {
        when(manufacturerService.list()).thenReturn(List.of(new Manufacturer(), new Manufacturer()));
        when(userService.getUserByPrincipal(principal)).thenReturn(new User());

        String viewName = adminController.adminmanufacturers(model, principal);

        verify(model).addAttribute("manufacturers", manufacturerService.list());
        verify(model).addAttribute("user", userService.getUserByPrincipal(principal));
        assertThat(viewName).isEqualTo("admin-manufacturers");
    }

    @Test
    public void testAdminEmployees() {
        when(employeeService.list()).thenReturn(List.of(new Employee(), new Employee()));
        when(userService.getUserByPrincipal(principal)).thenReturn(new User());

        String viewName = adminController.adminemployees(model, principal);

        verify(model).addAttribute("employees", employeeService.list());
        verify(model).addAttribute("user", userService.getUserByPrincipal(principal));
        assertThat(viewName).isEqualTo("admin-employees");
    }

    @Test
    public void testAdminOperators() {
        when(operatorService.list()).thenReturn(List.of(new Operator(), new Operator()));
        when(userService.getUserByPrincipal(principal)).thenReturn(new User());

        String viewName = adminController.adminoperators(model, principal);

        verify(model).addAttribute("operators", operatorService.list());
        verify(model).addAttribute("user", userService.getUserByPrincipal(principal));
        assertThat(viewName).isEqualTo("admin-operators");
    }

    @Test
    public void testAdminStos() {
        when(stoService.list()).thenReturn(List.of(new Sto()));
        when(userService.getUserByPrincipal(principal)).thenReturn(new User());

        String viewName = adminController.adminstos(model, principal);

        verify(model).addAttribute("stos", stoService.list());
        verify(model).addAttribute("user", userService.getUserByPrincipal(principal));
        assertThat(viewName).isEqualTo("admin-stos");
    }

    @Test
    public void testAdminReqs() {
        when(workerRequestService.list()).thenReturn(List.of(new WorkerRequest()));
        when(userService.getUserByPrincipal(principal)).thenReturn(new User());

        String viewName = adminController.adminreqs(model, principal);

        verify(model).addAttribute("reqs", workerRequestService.list());
        verify(model).addAttribute("user", userService.getUserByPrincipal(principal));
        assertThat(viewName).isEqualTo("admin-workerreqs");
    }

    @Test
    public void testAdminDetailProviders() {
        when(detailProviderService.list()).thenReturn(List.of(new DetailProvider()));
        when(userService.getUserByPrincipal(principal)).thenReturn(new User());

        String viewName = adminController.admindetailproviders(model, principal);

        verify(model).addAttribute("providers", detailProviderService.list());
        verify(model).addAttribute("user", userService.getUserByPrincipal(principal));
        assertThat(viewName).isEqualTo("admin-detailproviders");
    }

    @Test
    public void userBan_ShouldBanUserAndRedirect() {
        Long userId = 1L;

        String result = adminController.userBan(userId);

        verify(userService, times(1)).banUser(userId);
        assertEquals("redirect:/admin", result);
    }

    @Test
    public void userEdit_ShouldAddAttributesAndReturnViewName() {
        when(userService.getUserByPrincipal(principal)).thenReturn(user);
        when(stoService.list()).thenReturn(new ArrayList<>());

        String result = adminController.userEdit(user, model, principal);

        verify(model, times(1)).addAttribute("user", user);
        verify(model, times(1)).addAttribute("user1", user);
        verify(model, times(1)).addAttribute("roles", Role.values());
        verify(model, times(1)).addAttribute("stos", stoService.list());
        assertEquals("user-edit", result);
    }

    @Test
    public void userEdit_Post_ShouldChangeUserRolesAndRedirect() {
        Map<String, String> rolesMap = new HashMap<>();
        rolesMap.put("ROLE_USER", "true");

        String result = adminController.userEdit(user, rolesMap);

        verify(userService, times(1)).changeUserRoles(user, rolesMap);
        assertEquals("redirect:/admin/users", result);
    }

    @Test
    public void addWorker_ShouldSaveWorkerAndRedirect() {
        String specialization = "Dev";
        when(employeeService.getEmployee(user.getId())).thenReturn(employee);

        String result = adminController.addWorker(user, specialization);

        verify(workerService, times(1)).saveWorker(any(Worker.class));
        assertEquals("redirect:/admin/user/edit/1", result);
    }

    @Test
    void addAdmin_ShouldRedirectToUserEdit_WhenEmployeeIsFound() {
        when(employeeService.getEmployee(user.getId())).thenReturn(employee);

        String result = adminController.addAdmin(user);

        verify(employeeService).getEmployee(user.getId());
        verify(administratorService).saveAdmin(any(Administrator.class));
        assertEquals("redirect:/admin/user/edit/1", result);
    }

    @Test
    void addmanufacturer_ShouldRedirectToUserEdit_WhenEmployeeIsFound() {
        String detailSpecialization = "Detail Spec";
        when(employeeService.getEmployee(user.getId())).thenReturn(employee);

        String result = adminController.addmanufacturer(user, detailSpecialization);

        verify(employeeService).getEmployee(user.getId());
        verify(manufacturerService).saveManufacturer(any(Manufacturer.class));
        assertEquals("redirect:/admin/user/edit/1", result);
    }

    @Test
    public void testAddOperator() {
        String workingTimeStart = "09:00";
        String workingTimeEnd = "17:00";
        Employee employee = new Employee();
        when(employeeService.getEmployee(user.getId())).thenReturn(employee);

        String result = adminController.addoperator(user, workingTimeStart, workingTimeEnd);

        Operator operator = employee.getOperator();
        assertThat(operator).isNotNull();
        assertThat(operator.getWorkingTimeStart()).isEqualTo(LocalTime.parse(workingTimeStart));
        assertThat(operator.getWorkingTimeEnd()).isEqualTo(LocalTime.parse(workingTimeEnd));

        verify(operatorService).saveOperator(operator);
        assertThat(result).isEqualTo("redirect:/admin/user/edit/" + 1);
    }

    @Test
    public void testAddSto() {
        String name = "Test Sto";
        String phone = "1234567890";

        String result = adminController.addsto(user, name, phone);

        Sto sto = new Sto();
        sto.setName(name);
        sto.setPhone(phone);

        assertThat(result).isEqualTo("redirect:/admin/stos");
    }

    @Test
    public void testAddProvider() {
        String name = "Test Provider";
        String contact = "provider@example.com";

        String result = adminController.addProvider(user, name, contact);

        DetailProvider provider = new DetailProvider();
        provider.setName(name);
        provider.setContact(contact);

        assertThat(result).isEqualTo("redirect:/admin/detailproviders");
    }

    @Test
    public void testDeleteProduct_ValidProductId() {
        Long productId = 1L;
        doNothing().when(stoService).deleteSto(productId);

        adminController.deleteProduct(productId, principal);

        verify(stoService, times(1)).deleteSto(productId);
    }

    @Test
    public void testDeleteRequest_ValidRequestId() {
        Long requestId = 1L;
        doNothing().when(workerRequestService).deleteRequest(requestId);

        adminController.deleteRequest(requestId, principal);

        verify(workerRequestService, times(1)).deleteRequest(requestId);
    }

    @Test
    public void testStoEmployees() {
        Long stoId = 1L;
        Principal principal = mock(Principal.class);
        Model model = mock(Model.class);
        User user = new User();

        when(userService.getUserByPrincipal(principal)).thenReturn(user);
        when(stoService.getStoEmployees(stoId)).thenReturn(new HashSet<>());
        when(stoService.getSto(stoId)).thenReturn(new Sto());

        String result = adminController.stoemployees(model, principal, stoId);

        verify(model, times(1)).addAttribute("employees", stoService.getStoEmployees(stoId));
        verify(model, times(1)).addAttribute("user", user);
        verify(model, times(1)).addAttribute("sto", stoService.getSto(stoId));
        assertEquals("sto-employees", result);
    }

    @Test
    public void testProviderDetails() {
        Long providerId = 1L;
        Principal principal = mock(Principal.class);
        Model model = mock(Model.class);
        User user = new User();

        when(userService.getUserByPrincipal(principal)).thenReturn(user);
        when(detailProviderService.getProviderDetails(providerId)).thenReturn(Set.of(new Detail()));
        when(detailProviderService.getProvider(providerId)).thenReturn(new DetailProvider());

        String result = adminController.providerdetails(model, principal, providerId);

        verify(model, times(1)).addAttribute("details", detailProviderService.getProviderDetails(providerId));
        verify(model, times(1)).addAttribute("user", user);
        verify(model, times(1)).addAttribute("provider", detailProviderService.getProvider(providerId));
        assertEquals("provider-details", result);
    }

    @Test
    void testDeleteProvider() {
        Long providerId = 1L;
        Principal principal = mock(Principal.class);

        adminController.deleteProvider(providerId, principal);

        verify(detailProviderService, times(1)).deleteProvider(providerId);
    }

    @Test
    void testAddDetail() {
        String name = "New Detail";
        Integer stock = 10;
        Integer price = 100;
        String providerName = "TestProvider";
        User user = new User();
        DetailProvider detailProvider = new DetailProvider();
        detailProvider.setId(1L);

        when(detailProviderService.getProviderByName(providerName)).thenReturn(detailProvider);

        String result = adminController.addDetail(user, name, stock, price, providerName);

        verify(detailService, times(1)).saveDetail(any(Detail.class), eq(detailProvider));
        assertEquals("redirect:/admin/detailproviders", result);
    }

    @Test
    void testAddTool() {
        String name = "New Tool";
        Integer stock = 5;
        User user = new User();

        String result = adminController.addTool(user, name, stock);

        verify(toolService, times(1)).saveTool(any(Tool.class));
        assertEquals("redirect:/admin/tools", result);
    }

    @Test
    void testFillTool() {
        User user = new User();
        String toolName = "Hammer";
        Integer stock = 10;

        String result = adminController.fillTool(user, toolName, stock);

        verify(toolService).fill_tool_count_by_name(toolName, stock);
        assertEquals("redirect:/admin/tools", result);
    }
    @Test
    void testFillToolById() {
        User user = new User();
        String toolName = "Hammer";
        Integer stock = 10;

        Tool tool = new Tool();
        tool.setId(1L);

        when(toolService.getToolByName(toolName)).thenReturn(tool);

        String result = adminController.fillToolById(user, toolName, stock);

        verify(toolService).fill_tool_count(tool.getId(), stock);
        assertEquals("redirect:/admin/tools", result);
    }

    @Test
    void testDeleteTool() {
        Long toolId = 1L;

        Offer offer = new Offer();
        offer.setId(2L);
        List<Offer> offers = Arrays.asList(offer);

        when(offerService.listOffers("")).thenReturn(offers);

        String result = adminController.deleteTool(toolId);

        verify(offerService).removeToolFromOffer(offer.getId(), toolService.getToolById(toolId));
        assertEquals("redirect:/admin/tools", result);
    }

    @Test
    void testDeleteEmployeeFromSto() {
        String email = "test@example.com";
        Long employeeId = 1L;
        Long stoId = 2L;

        Employee employee = new Employee();
        when(employeeService.getEmployeeById(employeeId)).thenReturn(employee);

        String result = adminController.deleteEmployeeFromSto(email, employeeId, stoId);

        verify(stoService).removeEmployeeFromSto(stoId, employee);
        assertEquals("redirect:/admin/stos", result);
    }

    @Test
    void deleteDetailFromProvider() {
        Long providerId = 1L;
        Long detailId = 1L;
        String email = "test@example.com";
        Detail detail = new Detail();

        when(detailService.getDetailById(detailId)).thenReturn(detail);
        String result = adminController.deleteDetailFromProvider(email, detailId, providerId);

        verify(detailProviderService).removeDetailFromProvider(providerId, detail);
        assertEquals("redirect:/admin/detailproviders", result);
    }

    @Test
    void buyDetailFromProvider() {
        Long detailId = 1L;
        Long detailToBuyId = 2L;
        Integer storageStock = 10;
        Detail detailToBuy = new Detail();

        when(detailService.getDetailById(detailToBuyId)).thenReturn(detailToBuy);
        String result = adminController.buyDetailFromProvider("test@example.com", detailId, detailToBuyId, storageStock);

        verify(detailService).updateStorageStock(detailToBuy, storageStock);
        assertEquals("redirect:/admin/details", result);
    }

    @Test
    void admindetails() {
        Model model = mock(Model.class);
        Principal principal = mock(Principal.class);

        when(userService.getUserByPrincipal(principal)).thenReturn(new User());
        when(detailService.listStorage()).thenReturn(new ArrayList<>());

        String result = adminController.admindetails(model, principal);

        assertEquals("admin-details", result);
    }
    @Test
    void adminGetdetails() {
        when(principal.getName()).thenReturn("testuser");
        Integer num = 5;
        List<Detail> details = Arrays.asList(new Detail(), new Detail());

        when(detailService.listStorageIfBigger(num)).thenReturn(details);
        when(userService.getUserByPrincipal(principal)).thenReturn(new User());

        String result = adminController.adminGetdetails(model, principal, num);

        assertEquals("admin-available-details", result);
    }

    @Test
    void admintools() {
        when(principal.getName()).thenReturn("testuser");
        List<Tool> tools = Arrays.asList(new Tool(), new Tool());

        when(toolService.list()).thenReturn(tools);
        when(userService.getUserByPrincipal(principal)).thenReturn(new User());

        String result = adminController.admintools(model, principal);

        assertEquals("admin-tools", result);
    }
    @Test
    void adminavtools() {
        when(principal.getName()).thenReturn("testuser");
        List<Tool> availableTools = Arrays.asList(new Tool(), new Tool());

        when(toolService.listAvailable()).thenReturn(availableTools);
        when(userService.getUserByPrincipal(principal)).thenReturn(new User());

        String result = adminController.adminavtools(model, principal);

        assertEquals("admin-tools", result);
    }
    @Test
    void adminunavtools() {
        when(principal.getName()).thenReturn("testuser");
        List<Tool> unAvailableTools = Arrays.asList(new Tool(), new Tool());

        when(toolService.listUnAvailable()).thenReturn(unAvailableTools);
        when(userService.getUserByPrincipal(principal)).thenReturn(new User());

        String result = adminController.adminunavtools(model, principal);

        assertEquals("admin-tools", result);
    }

    @Test
    public void testAdminUnalloc_WithEmptyPurchases() {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("testUser");
        when(purchaseService.listUnalloc()).thenReturn(Collections.emptyList());
        when(userService.getUserByPrincipal(principal)).thenReturn(new User());

        String viewName = adminController.adminunalloc(model,principal);

        assertEquals("admin-unalloc", viewName);
        verify(model).addAttribute("purchases", Collections.emptyList());
    }

    @Test
    public void testAdminUnalloc_WithNonEmptyPurchases() {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("testUser");
        List<Purchase> mockPurchases = List.of(new Purchase(), new Purchase());
        when(purchaseService.listUnalloc()).thenReturn(mockPurchases);
        when(userService.getUserByPrincipal(principal)).thenReturn(new User());

        String viewName = adminController.adminunalloc(model,principal);

        assertEquals("admin-unalloc", viewName);
        verify(model).addAttribute("purchases", mockPurchases);
    }

    @Test
    public void testAdminUnalloc_WithNullPurchases() {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("testUser");
        when(purchaseService.listUnalloc()).thenReturn(null);
        when(userService.getUserByPrincipal(principal)).thenReturn(new User());

        String viewName = adminController.adminunalloc( model, principal);

        assertEquals("admin-unalloc", viewName);
        verify(model).addAttribute("purchases", null);
    }

    @Test
    public void testAdminAlloc() {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("testUser");
        when(purchaseService.listAlloc()).thenReturn(Collections.singletonList(new Purchase()));
        when(userService.getUserByPrincipal(principal)).thenReturn(new User());

        String viewName = adminController.adminalloc(model, principal);

        verify(model).addAttribute("purchases", purchaseService.listAlloc());
        verify(model).addAttribute("user", userService.getUserByPrincipal(principal));
        assertEquals("admin-alloc", viewName);
    }

    @Test
    public void testDeleteReview() {
        String offerName = "Test Offer";
        Long reviewId = 1L;
        List<String> offers = Collections.singletonList(offerName);
        Offer offer = new Offer();
        offer.setId(1L);
        Review review = new Review();

        when(offerService.getOfferByName(offerName)).thenReturn(offer);
        when(reviewService.getReview(reviewId)).thenReturn(review);

        String redirectUrl = adminController.deleteReview(offers, reviewId);

        verify(offerService).removeReviewFromOffer(offer.getId(), review);
        assertEquals("redirect:/user/reviews", redirectUrl);
    }
    @Test
    public void testAllocatePurchase() {
        Long purchaseId = 1L;
        Long workerId = 2L;

        User user = new User();
        user.setEmployee(new Employee()); // Установка необходимых полей
        user.getEmployee().setAdministrator(new Administrator());

        Purchase purchase = new Purchase();
        Worker worker = new Worker();

        when(purchaseService.getPurchase(purchaseId)).thenReturn(purchase);
        when(workerService.getWorker(workerId)).thenReturn(Optional.of(worker));

        String view = adminController.allocatePurchase(user, purchaseId, workerId);

        assertEquals("redirect:/admin/allocated/purchases", view);
        assertEquals(user.getEmployee().getAdministrator(), purchase.getAdministrator());
        assertEquals(worker, purchase.getWorker());
        verify(purchaseService).savePurchase(purchase);
    }

    @Test
    public void testGetSettings() {
        User user = new User();
        when(userService.getUserByPrincipal(principal)).thenReturn(user);

        String view = adminController.getSettings(model, principal);

        assertEquals("admin-settings", view);
        verify(model).addAttribute("user", user);
    }

    @Test
    public void testSaveSettings() {
        AppearanceSettings settings = new AppearanceSettings();

        String view = adminController.saveSettings(settings);

        assertEquals("redirect:/admin", view);
        verify(appearanceSettingsService).saveSettings(settings);
        verify(appearanceSettingsService).evict();
    }
    @Test
    public void testAddEmployee() {
        when(userService.getUserByEmail(user.getEmail())).thenReturn(user);
        Sto sto1 = new Sto();
        sto1.setId(1L);
        sto1.setName("sto1");

        Sto sto2 = new Sto();
        sto2.setId(2L);
        sto2.setName("sto2");

        when(stoService.getStoByName("sto1")).thenReturn(sto1);
        when(stoService.getStoByName("sto2")).thenReturn(sto2);

        String result = adminController.addEmployee(user, "123-456-789 01", form);

        assertEquals("redirect:/admin/user/edit/" + user.getId(), result);
    }

    @Test
    public void testPurchaseForm() {
        Purchase purchase = new Purchase();
        purchase.setId(1L);
        purchase.setStoName("sto1");

        when(purchaseService.getPurchase(1L)).thenReturn(purchase);
        when(stoService.getStoByName("sto1")).thenReturn(new Sto());


        String result = adminController.purchaseForm(user,1L, model);

        assertEquals("admin-alloc-form", result);
        verify(model).addAttribute("purchase", purchase);
    }
    @Test
    public void testAdminAddToToolDetail() throws Exception {
        Long id = 1L;
        Model model = mock(Model.class);
        Principal principal = mock(Principal.class);

        when(purchaseService.getPurchase(id)).thenReturn(new Purchase());
        when(toolService.list()).thenReturn(Collections.emptyList());
        when(detailService.listStorage()).thenReturn(Collections.emptyList());
        when(userService.getUserByPrincipal(principal)).thenReturn(new User());

        String viewName = adminController.adminaddtooldetail(model, principal, id);

        verify(model).addAttribute("purchase", purchaseService.getPurchase(id));
        verify(model).addAttribute("tools", toolService.list());
        verify(model).addAttribute("details", detailService.listStorage());
        verify(model).addAttribute("user", userService.getUserByPrincipal(principal));
        assertEquals("admin-add-tool-detail", viewName);
    }

    @Test
    public void testAdminAddToolDetailToOffer() throws Exception {
        Long id = 1L;
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("toolName", "Hammer");
        form.add("1", "2");

        Offer offer = new Offer();
        offer.setId(id);

        when(offerService.getOfferById(id)).thenReturn(offer);
        when(toolService.getToolByName("toolName")).thenReturn(new Tool());
        when(detailService.getDetailById(2L)).thenReturn(new Detail());

        String redirectUrl = adminController.adminAddToolDetailToOffer(form, id);

        assertEquals("redirect:/admin", redirectUrl);
    }
}