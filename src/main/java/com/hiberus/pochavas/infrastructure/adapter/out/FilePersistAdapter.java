package com.hiberus.pochavas.infrastructure.adapter.out;

import com.hiberus.pochavas.domain.models.ExcelTable;
import com.hiberus.pochavas.domain.models.TagSchema;
import com.hiberus.pochavas.domain.ports.out.FilePersistPort;
import com.hiberus.pochavas.infrastructure.adapter.out.table.TableEntity;
import com.hiberus.pochavas.infrastructure.adapter.out.table.TableMapper;
import com.hiberus.pochavas.infrastructure.adapter.out.table.TableRepository;
import com.hiberus.pochavas.infrastructure.adapter.out.tag.TagEntity;
import com.hiberus.pochavas.infrastructure.adapter.out.tag.TagMapper;
import com.hiberus.pochavas.infrastructure.adapter.out.tag.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class FilePersistAdapter implements FilePersistPort {

    private final TagRepository tagRepository;
    private final TableRepository tableRepository;
    private final TableMapper tableMapper;
    private final TagMapper tagMapper;


    @Override
    public void createTag(TagSchema tagSchema) {
        TagEntity tagToSave = tagMapper.tagSchemaDomainToEntity(tagSchema);
        tagRepository.save(tagToSave);
    }

    @Override
    public void deleteTag(TagSchema tagSchema) {
        TagEntity tagToDelete = tagMapper.tagSchemaDomainToEntity(tagSchema);
        tagRepository.deleteBySchemaAndTagAndStandarizedTag(tagToDelete.getSchema(),tagToDelete.getTag(),tagToDelete.getStandarizedTag());
    }

    @Override
    public List<TagSchema> getTagsFromSchema(String schema) {
        try{
            List<TagEntity> list = tagRepository.getBySchema(schema);
            List<TagSchema> result = new ArrayList<>();
            for(TagEntity t: list){
                result.add(tagMapper.tagSchemaEntityToDomain(t));
            }
            return result;

        }catch (Exception e){
            return null;
        }

    }

    @Override
    public void saveTable(ExcelTable table) {
        TableEntity tableEntity = tableMapper.tableDomainToEntity(table);
        tableRepository.save(tableEntity);
    }

    @Override
    public List<ExcelTable> getTablesFromSchema(String schema) {
        try{
            List<TableEntity> list = tableRepository.getByFileId(schema);
            List<ExcelTable> result = new ArrayList<>();
            for(TableEntity t: list){
                result.add(tableMapper.tableEntityToDomain(t));
            }
            return result;

        }catch (Exception e){
            return null;
        }
    }


}
