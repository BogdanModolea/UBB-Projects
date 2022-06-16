class UndoException(Exception):
    pass

class UndoErrorException(UndoException):
    pass

class UndoObject:
    def __init__(self, undo_function, redo_function):
        self.undo_function = undo_function
        self.redo_function = redo_function

class UndoService:
    def __init__(self, person_repo, activity_repo):
        self._person_repo = person_repo
        self._activity_repo = activity_repo
        self._undo_stack = []
        self._undo_pointer = 0

    def register_operation(self, operation):
        """
        Storing an operation that we want to undo / redo
        :param operation:
        :return:
        """
        self._normalise_stack()
        self._undo_stack.append(operation)
        self._undo_pointer += 1

    def _normalise_stack(self):
        """
        Undo/redo operations must cascade
        :return:
        """
        while len(self._undo_stack) != self._undo_pointer:
            self._undo_stack.pop()

    def undo(self):
        """
        Undoes the last performed operation
        :return:
        """
        if self._undo_pointer == 0:
            raise UndoErrorException("No operation to undo")
        self._undo_pointer -= 1
        self._undo_stack[self._undo_pointer].undo_function()

    def redo(self):
        """
        Redoes the last undone operation
        :return: None
        """
        if self._undo_pointer == len(self._undo_stack):
            raise UndoErrorException("No operation to redo")
        self._undo_stack[self._undo_pointer].redo_function()
        self._undo_pointer += 1