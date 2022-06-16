"""
  Start the program by running this module
"""

from functions import test_command_args, test_create_number, test_get_modulo, test_replace_number, test_remove_number, test_insert_number, test_add_number, test_sum, test_product, test_filter, test_undo
from ui import cmd_menu

if __name__ == '__main__':
    test_command_args()
    test_create_number()
    test_get_modulo()
    test_replace_number()
    test_remove_number()
    test_insert_number()
    test_add_number()
    test_sum()
    test_product()
    test_filter()
    test_undo()
    cmd_menu()