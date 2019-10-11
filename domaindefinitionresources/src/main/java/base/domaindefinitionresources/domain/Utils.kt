package base.domaindefinitionresources.domain

internal fun escapeDomainString(string: String): String {
    return string
            .toLowerCase()
            .trim()
            .replace('ç', 'c')
            .replace('á', 'a')
            .replace('à', 'a')
            .replace('â', 'a')
            .replace('ã', 'a')
            .replace('é', 'e')
            .replace('è', 'e')
            .replace('ê', 'e')
            .replace('í', 'i')
}