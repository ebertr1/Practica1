import enum
class enumTipoRuc (enum.Enum):
    EDUCATIVO = 'EDUCATIVO'
    PROFESIONAL = 'PROFESIONAL'

    def getValues(self):
        return self.value

    def __str__(self) -> str:
        return self.value