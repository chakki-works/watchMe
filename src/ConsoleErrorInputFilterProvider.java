import com.intellij.execution.filters.ConsoleInputFilterProvider;
import com.intellij.execution.filters.InputFilter;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

import com.chakki_works.watchme.SlackPost;

public class ConsoleErrorInputFilterProvider implements ConsoleInputFilterProvider {
    @NotNull
    @Override
    public InputFilter[] getDefaultFilters(@NotNull Project project) {
        InputFilter consoleErrorFilter = new ConsoleErrorInputFilter(project);
        return new InputFilter[]{consoleErrorFilter};
    }
}