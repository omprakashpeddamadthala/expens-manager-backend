INSERT INTO tbl_expense (expense_id, name, note, category, date, amount, created_at, updated_at)
VALUES
    (UUID(), 'Test Expense', 'This is a test expense', 'Food', '2024-12-10', 100.00, '2024-12-10 00:00:00', '2024-12-10 00:00:00');

INSERT INTO tbl_expense (expense_id, name, note, category, date, amount, created_at, updated_at)
VALUES
    (UUID(), 'Another Test Expense', 'This is another test expense', 'Transportation', '2024-12-11', 75.50, '2024-12-11 00:00:00', '2024-12-11 00:00:00');
