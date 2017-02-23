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
        return new InputFilter[]{new InputFilter() {
            @Nullable
            @Override
            public List<Pair<String, ConsoleViewContentType>> applyFilter(String text, ConsoleViewContentType outputType) {
                //System.out.print(text);
                if (outputType.equals(ConsoleViewContentType.ERROR_OUTPUT)) {
                    System.out.print(text);
                }
                if (outputType.equals(ConsoleViewContentType.SYSTEM_OUTPUT) && !text.contains("exit code")) {
                    return Collections.emptyList();
                }
                //if (text.startsWith(PyRunCythonExtensionsFilter.WARNING_MESSAGE_BEGIN)) {
                //    return Collections.emptyList();
                //}
                if (text.startsWith("pydev debugger")) {
                    return Collections.emptyList();
                }
                return Collections.singletonList(Pair.create(text, outputType));
            }
        }};
    }
}