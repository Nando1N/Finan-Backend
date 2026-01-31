@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository; // 👈 Precisamos disso para buscar o usuário real

    public void criarDespesaParcelado(Expense despesaBase, String userId) {
        UUID groupId = UUID.randomUUID();
        int total = despesaBase.getTotalInstallments();

        // 🔍 Buscamos o usuário no banco pelo ID que veio do Token
        User usuarioLogado = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        for (int i = 1; i <= total; i++) {
            Expense parcela = new Expense();

            parcela.setDescription(despesaBase.getDescription());
            parcela.setAmount(despesaBase.getAmount());

            // 🔗 Vinculamos o objeto User real à parcela
            parcela.setUser(usuarioLogado);

            parcela.setTransactionGroupId(groupId);
            parcela.setCurrentInstallment(i);
            parcela.setTotalInstallments(total);
            parcela.setDueDate(despesaBase.getDueDate().plusMonths(i - 1));

            expenseRepository.save(parcela);
        }
    }
}