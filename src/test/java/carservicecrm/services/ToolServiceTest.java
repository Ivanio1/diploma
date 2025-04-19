package carservicecrm.services;

import carservicecrm.models.Tool;
import carservicecrm.repositories.ToolRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ToolServiceTest {

    @Mock
    private ToolRepository toolRepository;

    @InjectMocks
    private ToolService toolService;

    private Tool tool;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tool = new Tool();
        tool.setId(1L);
        tool.setName("Hammer");
        tool.setStock(10);
    }

    @Test
    void saveTool_NewTool() {
        when(toolRepository.findToolByName(tool.getName())).thenReturn(null);
        boolean result = toolService.saveTool(tool);
        assertTrue(result);
        verify(toolRepository).save(tool);
    }

    @Test
    void saveTool_ExistingTool() {
        Tool existingTool = new Tool();
        existingTool.setId(2L);
        existingTool.setName("Hammer");
        existingTool.setStock(5);

        when(toolRepository.findToolByName(tool.getName())).thenReturn(existingTool);
        boolean result = toolService.saveTool(tool);

        assertTrue(result);
        assertEquals(15, existingTool.getStock());
        verify(toolRepository).save(existingTool);
    }

    @Test
    void saveTool_ExceptionThrown() {
        when(toolRepository.findToolByName(tool.getName())).thenThrow(new RuntimeException());
        boolean result = toolService.saveTool(tool);
        assertFalse(result);
    }

    @Test
    void deleteTool_ToolExists() {
        when(toolRepository.findById(1L)).thenReturn(Optional.of(tool));
        toolService.deleteTool(1L);
        verify(toolRepository).deleteById(tool.getId());
    }

    @Test
    void deleteTool_ToolDoesNotExist() {
        when(toolRepository.findById(1L)).thenReturn(Optional.empty());
        toolService.deleteTool(1L);
        verify(toolRepository, never()).deleteById(anyLong());
    }

    @Test
    void getToolById() {
        when(toolRepository.findToolById(1L)).thenReturn(tool);
        Tool result = toolService.getToolById(1L);
        assertEquals(tool, result);
    }

    @Test
    void getToolByName() {
        when(toolRepository.findToolByName("Hammer")).thenReturn(tool);
        Tool result = toolService.getToolByName("Hammer");
        assertEquals(tool, result);
    }

    @Test
    void fill_tool_count() {
        toolService.fill_tool_count(1L, 5);
        verify(toolRepository).fillToolCount(1L, 5);
    }

    @Test
    void fill_tool_count_by_name() {
        toolService.fill_tool_count_by_name("Hammer", 5);
        verify(toolRepository).fillToolCountByName("Hammer", 5);
    }

    @Test
    void list() {
        List<Tool> tools = Arrays.asList(tool);
        when(toolRepository.findAllTools()).thenReturn(tools);
        List<Tool> result = toolService.list();
        assertEquals(tools, result);
    }

    @Test
    void listAvailable() {
        List<Tool> availableTools = Arrays.asList(tool);
        when(toolRepository.get_available_tools()).thenReturn(availableTools);
        List<Tool> result = toolService.listAvailable();
        assertEquals(availableTools, result);
    }

    @Test
    void listUnAvailable() {
        List<Tool> unavailableTools = Arrays.asList(tool);
        when(toolRepository.get_zero_tools()).thenReturn(unavailableTools);
        List<Tool> result = toolService.listUnAvailable();
        assertEquals(unavailableTools, result);
    }
}