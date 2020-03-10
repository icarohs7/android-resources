package base.domaindefinitionresources.domain

import java.util.Locale

internal fun escapeDomainString(string: String): String {
    return string
        .toLowerCase(Locale.getDefault())
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